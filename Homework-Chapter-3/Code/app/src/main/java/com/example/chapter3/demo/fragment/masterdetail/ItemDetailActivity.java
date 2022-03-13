package com.example.chapter3.demo.fragment.masterdetail;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.chapter3.demo.R;

public class ItemDetailActivity extends FragmentActivity {
	ItemDetailFragment fragmentItemDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		// 从Intent里拿出item参数
		Item item = (Item) getIntent().getSerializableExtra("item");
		if (savedInstanceState == null) {
			// TODO
		}
	}

}
