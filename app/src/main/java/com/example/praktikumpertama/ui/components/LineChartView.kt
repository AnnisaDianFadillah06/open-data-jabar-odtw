package com.example.praktikumpertama.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.praktikumpertama.data.DataEntity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Composable
fun LineChartView(dataList: List<DataEntity>) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        factory = { ctx ->
            LineChart(ctx).apply {
                description.isEnabled = false

                // Konfigurasi Sumbu X
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    textSize = 12f
                    labelRotationAngle = -45f  // Putar label agar lebih terbaca
                }

                // Konfigurasi Sumbu Y
                axisLeft.apply {
                    setDrawGridLines(true)
                    textSize = 12f
                }

                axisRight.isEnabled = false  // Matikan sumbu kanan

                legend.isEnabled = true // Aktifkan legend
            }
        },
        update = { chart ->
            val entries = dataList.mapIndexed { index, data ->
                Entry(index.toFloat(), data.total.toFloat()) // Tidak perlu data.label
            }

            // Warna berbeda untuk setiap provinsi
            val colors = listOf(
                Color.Blue.toArgb(),
                Color.Red.toArgb(),
                Color.Green.toArgb(),
                Color.Cyan.toArgb(),
                Color.Magenta.toArgb()
            )

            val lineDataSet = LineDataSet(entries, "Total Populasi").apply {
                valueTextColor = Color.White.toArgb()
                lineWidth = 2f
                setDrawValues(true) // Tampilkan angka di atas titik data
                setCircleColors(colors) // Warna lingkaran berbeda
                setDrawCircleHole(false)
                setDrawFilled(true)
                fillColor = Color.LightGray.toArgb()
            }

            val lineData = LineData(lineDataSet)
            chart.data = lineData

            // **Tambahkan label sumbu X berdasarkan nama provinsi**
            chart.xAxis.valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return dataList.getOrNull(value.toInt())?.namaProvinsi ?: "" // Ambil nama provinsi
                }
            }

            chart.invalidate() // Refresh chart setelah update
        }
    )
}
