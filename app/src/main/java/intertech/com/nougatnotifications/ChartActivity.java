package intertech.com.nougatnotifications;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ChartActivity extends AppCompatActivity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mChart = findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.getLegend().setEnabled(false);
        mChart.setNoDataTextColor(ContextCompat.getColor(this, R.color.grey));
        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        // add data
        setData(100, 30);
        mChart.setExtraOffsets(8f, 0f, 0f, 10f);
        setAxisInfo();
        mChart.invalidate();
    }

    private void setAxisInfo() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(font);
        xAxis.setTextSize(10f);
        xAxis.setAxisLineColor(ContextCompat.getColor(ChartActivity.this, R.color.grey));
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(6);
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.grey));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
//        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setTypeface(font);
        leftAxis.setAxisLineColor(ContextCompat.getColor(ChartActivity.this, R.color.grey));
        leftAxis.setDrawGridLines(false);
//        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(180f);
        leftAxis.setTextColor(ContextCompat.getColor(this, R.color.grey));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setData(int count, float range) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<>();

        float from = now;

        // count = hours
        float to = now + count;

        // increment by 1 hour
        for (float x = from; x < to; x++) {

            float y = getRandom(range, 50);
            values.add(new Entry(x, y)); // add one entry per hour
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, null);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setMode(LineDataSet.Mode.LINEAR);
        set1.setLineWidth(2.0f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setDrawHighlightIndicators(false);
//        set1.setFillAlpha(65);
//        set1.setFillColor(ColorTemplate.getHoloBlue());
//        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    /*private void getChartData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("timespan", "1days");
        map.put("rollingAverage", "8hours");
        map.put("format", "json");
        Call<ChartResponse> call = RetrofitRestClient.getChartApi().chartData(map);
        call.enqueue(new CustomRetrofitCallback<ChartResponse>(ChartActivity.this) {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                super.onFailure(call, t);
                t.printStackTrace();
                AlertDialogUtils.SimpleAlertDialog(ChartActivity.this, "Server Error", "Unable to connect to server\nplease try again later");
            }
        });
    }*/

    private void setTimespan(String timespan) {
        float granularity;
        switch (timespan) {
            case "all_time":
                granularity = 60 * 60 * 24 * 365F;
                break;
            case "year":
                granularity = 60 * 60 * 24 * 30F;
                break;
            case "month":
                granularity = 60 * 60 * 24 * 2F;
                break;
            case "day":
                granularity = 60 * 60 * 4F;
                break;
        }
    }
}


//Base url: https://api.blockchain.info/price/
//        base: btc
//        quote: usd
//        time: epoch seconds time in the past
//        scale: difference between two prices (in seconds)

//    @GET("index-series")
//    Observable<List<PriceDatum>> getHistoricPriceSeries(@Query("base") String base,
//                                                        @Query("quote") String quote,
//                                                        @Query("start") long start,
//                                                        @Query("scale") int scale,
//                                                        @Query("api_key") String apiKey);


/*
Scale:
TimeSpan.ALL_TIME -> Scale.FIVE_DAYS
        TimeSpan.YEAR -> Scale.ONE_DAY
        TimeSpan.MONTH -> Scale.TWO_HOURS
        TimeSpan.WEEK -> Scale.ONE_HOUR
        TimeSpan.DAY -> Scale.FIFTEEN_MINUTES*/

/* public static final int FIFTEEN_MINUTES = 900;
    public static final int ONE_HOUR = 3600;
    public static final int TWO_HOURS = 7200;
    public static final int ONE_DAY = 86400;
    public static final int FIVE_DAYS = 432000;*/


/* for start time
*    private fun getStartTimeForTimeSpan(timeSpan: TimeSpan, cryptoCurrency: CryptoCurrencies): Long {
        val start = when (timeSpan) {
            TimeSpan.ALL_TIME -> return getFirstMeasurement(cryptoCurrency)
            TimeSpan.YEAR -> 365
            TimeSpan.MONTH -> 30
            TimeSpan.WEEK -> 7
            TimeSpan.DAY -> 1
        }

        val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -start) }
        return cal.timeInMillis / 1000
    }
* */
