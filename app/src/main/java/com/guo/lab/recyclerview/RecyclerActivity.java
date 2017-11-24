package com.guo.lab.recyclerview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.guo.lab.R;
import com.guo.lab.databinding.ActivityRecyclerBinding;
import com.guo.lab.databinding.ItemHeroBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] heros = {"武松", "林冲", "鲁达", "张清", "武松", "林冲", "鲁达", "张清", "武松",
            "林冲", "鲁达", "张清", "武松", "林冲", "鲁达", "张清", "武松", "林冲", "鲁达", "张清"};
    private List<String> heroList = new ArrayList<>();
    private ActivityRecyclerBinding binding;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler);
        binding.setClick(this);

        for (int i = 0; i < heros.length; i++) {
            heroList.add(heros[i]);
        }
        mAdapter = new HeroAdapter(heroList);
        binding.recycler.setAdapter(mAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
//        binding.recycler.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public void onClick(View v) {
        Random random = new Random();

        switch (v.getId()) {
            case R.id.add: {
                int position = random.nextInt(heros.length);
                heroList.add(Math.min(heroList.size(), position), heros[position]);
                mAdapter.notifyItemInserted(position);

            }
            break;
            case R.id.move: {
                int size = heroList.size();
                if (size < 2) return;
                int from = random.nextInt(size);
                int to = random.nextInt(size);
                mAdapter.notifyItemMoved(from, to);
            }
            break;
            case R.id.remove:
                int position = random.nextInt(mAdapter.getItemCount());
                heroList.remove(position);
                mAdapter.notifyItemRemoved(position);
                break;
        }
    }

    static class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {

        private List<String> data;

        public HeroAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemHeroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(Utils.getContext()), R.layout.item_hero, parent, false);
            HeroViewHolder holder = new HeroViewHolder(binding.getRoot());
            holder.heroText = binding.hero;

            return holder;
        }

        @Override
        public void onBindViewHolder(HeroViewHolder holder, int position) {
            holder.heroText.setText(data.get(position));
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder {

            TextView heroText;

            public HeroViewHolder(View itemView) {
                super(itemView);
            }

        }
    }

}
