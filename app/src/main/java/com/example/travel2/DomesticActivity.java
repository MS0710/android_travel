package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DomesticActivity extends AppCompatActivity {
    private String TAG = "DomesticActivity";
    private ListView lv_domestic_list;
    private List<Domestic> list;
    private DomesticListAdapter domesticListAdapter;
    private String[][] data = new String[15][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic);
        initView();
    }

    private void initView(){
        lv_domestic_list = (ListView) findViewById(R.id.lv_domestic_list);
        list = new ArrayList<>();
        //Domestic domestic = new Domestic("","","","");
        //list.add(domestic);
        setData();
        domesticListAdapter = new DomesticListAdapter(getApplicationContext(),list);
        lv_domestic_list.setAdapter(domesticListAdapter);
    }

    private void setData(){
        int i;
        Domestic domestic;

        i = 0;
        data[i][0] = "臺北國際航空站（臺北松山機場）";
        data[i][1] = "臺北市松山區敦化北路340-9號";
        data[i][2] = "+886-2-87703430（國際線）、+886-2-87703460（國內線）、+886-2-87703456（語音查詢）";
        data[i][3] = "花蓮、臺東、澎湖、金門、南竿、北竿。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 1;
        data[i][0] = "臺中航空站（臺中國際機場）";
        data[i][1] = "臺中市沙鹿區中航路一段168號";
        data[i][2] = "+886-4-26155000";
        data[i][3] = "花蓮、澎湖、金門、南竿。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 2;
        data[i][0] = "嘉義航空站（嘉義水上機場）";
        data[i][1] = "嘉義縣水上鄉三和村榮典路1號";
        data[i][2] = "+886-5-2867886";
        data[i][3] = "澎湖、金門。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 3;
        data[i][0] = "臺南航空站（臺南機場）";
        data[i][1] = "臺南市南區機場路775號";
        data[i][2] = "+886-6-2601016";
        data[i][3] = "澎湖、金門。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 4;
        data[i][0] = "高雄國際航空站（高雄國際機場）";
        data[i][1] = "高雄市小港區中山四路2號";
        data[i][2] = "+886-7-8057630（國內線）、+886-7-8057631（國際線）";
        data[i][3] = "花蓮、澎湖、望安、七美、金門。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 5;
        data[i][0] = "恆春航空站（恆春機場）";
        data[i][1] = "屏東縣恆春鎮省北路二段393號";
        data[i][2] = "+886-8-8897120";
        data[i][3] = "無";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 6;
        data[i][0] = "花蓮航空站（花蓮機場）";
        data[i][1] = "花蓮縣新城鄉嘉里村機場1號";
        data[i][2] = "+886-3-8210768";
        data[i][3] = "臺北、臺中、高雄。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 7;
        data[i][0] = "臺東航空站（臺東豐年機場）";
        data[i][1] = "臺東縣臺東市民航路1100號";
        data[i][2] = "+886-89-361111";
        data[i][3] = "臺北、綠島、蘭嶼。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 8;
        data[i][0] = "綠島航空站（綠島機場）";
        data[i][1] = "臺東縣綠島鄉南寮村231號";
        data[i][2] = "+886-89-671194";
        data[i][3] = "臺東。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 9;
        data[i][0] = "蘭嶼航空站（蘭嶼機場）";
        data[i][1] = "臺東縣蘭嶼鄉紅頭村漁人151號";
        data[i][2] = "+886-89-732220";
        data[i][3] = "臺東。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 10;
        data[i][0] = "馬公航空站（澎湖機場）";
        data[i][1] = "澎湖縣湖西鄉隘門村126-5號";
        data[i][2] = "+886-6-9229123";
        data[i][3] = "臺北、臺中、嘉義、臺南、高雄、七美、金門。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 11;
        data[i][0] = "望安航空站（望安機場）";
        data[i][1] = "澎湖縣望安鄉中社村156號";
        data[i][2] = "+886-6-9991806";
        data[i][3] = "高雄。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 12;
        data[i][0] = "七美航空站（七美機場）";
        data[i][1] = "澎湖縣七美鄉平和村55號";
        data[i][2] = "+886-6-9971256";
        data[i][3] = "高雄、澎湖。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 13;
        data[i][0] = "金門航空站（金門尚義機場）";
        data[i][1] = "金門縣金湖鎮尚義機場2號";
        data[i][2] = "+886-82-322381、313694（人工）、+886-82-322383、313666（語音）";
        data[i][3] = "臺北、臺中、嘉義、臺南、高雄、澎湖。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 13;
        data[i][0] = "南竿航空站（馬祖南竿機場）";
        data[i][1] = "連江縣南竿鄉復興村220號";
        data[i][2] = "+886-836-26505";
        data[i][3] = "臺北、臺中。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

        i = 14;
        data[i][0] = "北竿航空站（馬祖北竿機場）";
        data[i][1] = "連江縣北竿鄉塘岐村261-2號";
        data[i][2] = "+886-836-56606#105（服務時間：08:00~17:00）、+886-836-55657（服務時間：17:00~08:00）";
        data[i][3] = "臺北。";
        domestic = new Domestic(data[i][0],data[i][2],data[i][1],data[i][3]);
        list.add(domestic);

    }
}