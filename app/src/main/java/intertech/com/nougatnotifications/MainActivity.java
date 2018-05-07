package intertech.com.nougatnotifications;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ask.com.askutilitylibraryandroid.AskUtility;
import ask.com.askutilitylibraryandroid.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    PieChart pieChart;
    private TextView ipAddressTv;
    private AskUtility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        ButterKnife.bind(this);
        utility = new AskUtility(this);
        Button inlineButton = (Button) findViewById(R.id.inlineResponse);
        Button bundledButton = (Button) findViewById(R.id.bundled);
        bundledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtil.bundledNotification();
            }
        });
        Gson gson = new Gson();
        Log.d("test", "onCreate: " + NetworkUtils.isNetworkAvailable(this));
        setChartData();
    }

    private void setChartData() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, ""));
        PieDataSet set = new PieDataSet(entries, "Balance");
        set.setColor(Color.parseColor("#3F51B5"));
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.setCenterTextSize(utility.dpToPx(10));
        pieChart.setCenterText("$1,235,643,212");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(75.0f);
        pieChart.invalidate(); // refresh
    }
}
