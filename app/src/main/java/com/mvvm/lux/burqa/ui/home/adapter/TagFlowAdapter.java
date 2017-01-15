package com.mvvm.lux.burqa.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/15 15:37
 * @Version
 */
public class TagFlowAdapter extends TagAdapter<ComicResponse.ChaptersBean.DataBean> {

    public TagFlowAdapter(List<ComicResponse.ChaptersBean.DataBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, ComicResponse.ChaptersBean.DataBean dataBean) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tags_item, parent, false);
        textView.setText(dataBean.getChapter_title());
        return textView;
    }

}
