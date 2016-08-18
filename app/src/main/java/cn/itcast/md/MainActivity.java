package cn.itcast.md;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.md.adapter.MainAdapter;
import cn.itcast.md.fragment.ItemFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initDrawerLayout();
        initNavigationView();
        initFAB();
        initViewPager();
    }

    /**
     * 初始化ViewPager与选项卡
     */
    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragments = new ArrayList<>();
        String[] titles = {
                "列表", "网格","瀑布流"
        };

        int[] layoutTypes =  {
                ItemFragment.TYPE_LIST_VIEW,
                ItemFragment.TYPE_GRID_VIEW,
                ItemFragment.TYPE_STAGGERED
        };

        for (int i = 0; i < titles.length; i++) {
            ItemFragment fragment = new ItemFragment();

            // activity传递参数给Fragment
            Bundle bundle = new Bundle();
            bundle.putInt("type", layoutTypes[i]);
            fragment.setArguments(bundle);

            fragments.add(fragment);
        }

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), fragments, titles));
        // 关联选项卡控件与ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化浮动操作按钮
     */
    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化侧滑菜单
     */
    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // 设置菜单项点击事件
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.nav_camera) {

                        }
                        // 关闭抽屉
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
        });
    }

    /**
     * 初始化抽屉控件
     */
    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 创建开关控件： 用来关联DrawerLayout与ActionBar(ToolBar)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        // 设置监听器
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState(); // 同步DrawerLayout与ActionBar状态
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);     // 使用toolBar代替ActionBar

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示导航按钮
        toolbar.setNavigationIcon(android.R.drawable.ic_delete);// 设置导航按钮

        toolbar.setLogo(R.mipmap.ic_launcher);          // 设置logo图片
        toolbar.setTitle("ToolBar Title");              // 设置标题
        toolbar.setTitleTextColor(Color.RED);           // 设置标题颜色
        toolbar.setSubtitle("This is subtitle");        // 设置子标题
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Toast.makeText(MainActivity.this, "分享", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // 按下返回键时，如果抽屉打开了则关闭它
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else { // 按下返回键时，退出当前activity界面
            super.onBackPressed();
        }
    }
}
