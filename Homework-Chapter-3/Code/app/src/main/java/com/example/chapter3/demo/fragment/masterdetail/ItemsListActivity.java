package com.example.chapter3.demo.fragment.masterdetail;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.example.chapter3.demo.R;

// 实现 OnItemSelectedListener 监听
public class ItemsListActivity extends FragmentActivity implements ItemsListFragment.OnItemSelectedListener {
	// 判断横竖屏
	private boolean isTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		determinePaneLayout();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentItemDetail = findViewById(R.id.flDetailContainer);
		if (fragmentItemDetail != null) {
			isTwoPane = true;
			ItemsListFragment fragmentItemsList = 
					(ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
			// 打开内部ListView的激活状态
			fragmentItemsList.setActivateOnItemClick(true);
		}
	}

	/**
	 * 当Item被选择的时候回调
	 * @param item Item的内容对象
	 */
	@Override
	public void onItemSelected(Item item) {
		if (isTwoPane) { // single activity with list and detail
		} else { // separate activities
		}
	}

}
