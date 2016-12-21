package dimas.herwin.latif.com.getgood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityDetailActivity extends AppCompatActivity {

    public final static String COMMUNITY_ID = "dimas.herwin.latif.com.getgood.COMMUNITY_ID";
    private String communityId = null;

    private TextView    nameView;
    private ImageView   imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
    }
}
