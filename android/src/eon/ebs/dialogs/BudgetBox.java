package eon.ebs.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mygdx.game.R;
import eon.ebs.entities.Budget;
import eon.ebs.entities.Player;

import java.util.ArrayList;

public class BudgetBox extends Activity {

    private Player player = null;

    public BudgetBox() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.budget_dialog);

        player = (Player) getIntent().getSerializableExtra("PLAYERINFO");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        ArrayList<Budget> map = player.getBudgetList();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        for(int i = 0; i < map.size(); i++) {
            Budget budget = map.get(i);
            series.appendData(new DataPoint(budget.getDate(),budget.getBudget()), true, 200000);
        }

        graph.addSeries(series);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1 - Statistik
        TabHost.TabSpec spec = host.newTabSpec("Statistik");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Statistik");
        host.addTab(spec);

        //Tab 2 - Budget
        spec = host.newTabSpec("Budget");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Budget");
        host.addTab(spec);

        //Tab 3 - Bilanz
        spec = host.newTabSpec("Bilanz");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Bilanz");
        host.addTab(spec);
    }

}