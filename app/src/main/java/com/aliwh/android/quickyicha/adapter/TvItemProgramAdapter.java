package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.activity.TvLiveActivity;

/**
 * 电视频道 适配器
 * @author liwh
 * @date 2019/3/5
 */
public class TvItemProgramAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mDataList = new String[]{
            "CCTV1综合",
            "CCTV2财经",
            "CCTV3综艺",
            "CCTV4中文国际",
            "CCTV5体育",
            "CCTV6电影",
            "CCTV7军事农业",
            "CCTV8电视剧",
            "CCTV9记录(不可用)",
            "CCTV10科教",
            "CCTV11戏曲",
            "CCTV12社会与法",
            "CCTV13新闻",
            "CCTV14少儿",
            "CCTV15音乐",
            "浙江卫视",
            "江苏卫视",
            "湖南卫视(不可用)",
            "北京卫视",
            "东方卫视",
            "湖北卫视",
            "天津卫视",
            "广东卫视",
            "安徽卫视",
            "山东卫视",
            "江西卫视",
            "辽宁卫视",
            "黑龙江卫视",
            "吉林卫视",
            "重庆卫视",
            "东南卫视",
            "贵州卫视（不可用）",
            "河北卫视",
            "四川卫视",
            "河南卫视",
            "广西卫视",
            "山西卫视",
            "青海卫视",
            "内蒙古卫视",
            "云南卫视",
            "宁夏卫视",
            "新疆卫视",
            "甘肃卫视",
            "陕西卫视",
            "西藏卫视",
            "海峡卫视",
            "凤凰卫视中文台",
            "凤凰卫视资讯台",
            "凤凰香港",
            "凤凰美洲",
            "香港HKS",
            "星空卫视"
    };

    private String[] mUrlList = new String[]{
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-1-CQ/G_CCTV-1-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-2-CQ/G_CCTV-2-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-3-CQ/G_CCTV-3-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-4-CQ/G_CCTV-4-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-5-CQ/G_CCTV-5-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-6-CQ/G_CCTV-6-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-7-HQ/G_CCTV-7-HQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-8-CQ/G_CCTV-8-CQ/",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-9-HQ/G_CCTV-9-HQ/",
            "http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227317/index.m3u8",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-11-HQ/G_CCTV-11-HQ/",
            "http://117.169.72.6:8080/PLTV/88888888/224/3221225641/index.m3u8",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-13-HQ/G_CCTV-13-HQ/",
            "http://39.134.52.167/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226057/index.m3u8",
            "http://223.110.242.130:6610/gitv/live1/G_CCTV-15/G_CCTV-15/",
            "http://223.110.245.159/ott.js.chinamobile.com/PLTV/3/224/3221227393/index.m3u8",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=1",
            "rtsp://111.48.16.199:1554/PLTV/88888888/224/3221225686/10000100000000060000000000252974_0.smil",
            "http://223.110.245.163/ott.js.chinamobile.com/PLTV/3/224/3221227436/index.m3u8",
            "http://223.110.245.159/ott.js.chinamobile.com/PLTV/3/224/3221227396/index.m3u8",
            "http://118.123.60.8:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_TBt=614400&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_cid=6653&Fsv_chan_hls_se_idx=5",
            "http://223.110.243.170/PLTV/3/224/3221227212/index.m3u8",
            "http://223.110.243.136/PLTV/3/224/3221227249/index.m3u8",
            "http://39.134.52.168/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226045/index.m3u8",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=5",
            "http://223.110.245.170/PLTV/3/224/3221225536/index.m3u8",
            "http://223.110.245.145/ott.js.chinamobile.com/PLTV/3/224/3221227410/index.m3u8",
            "http://223.110.243.169/PLTV/3/224/3221227252/index.m3u8",
            "http://223.110.245.153/ott.js.chinamobile.com/PLTV/3/224/3221225883/index.m3u8",
            "http://39.134.52.162/hwottcdn.ln.chinamobile.com/PLTV/88888890/224/3221226169/index.m3u8",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=13",
            "rtsp://119.39.49.116:554/ch00000090990000001220.sdp?vcdnid=001",
            "http://live01.hebtv.com/channels/hebtv/video_channel_04/flv:2000k/live",
            "http://118.123.60.12:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6449&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=0",
            "http://223.110.245.157/ott.js.chinamobile.com/PLTV/3/224/3221225815/index.m3u8",
            "http://223.110.242.130:6610/gitv/live1/GXWS/GXWS/",
            "http://223.110.242.130:6610/gitv/live1/SXWS/SXWS/",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=16",
            "http://223.110.245.139:80/PLTV/4/224/3221225836/index.m3u8",
            "http://edge1.yntv.cn/channels/yntv/ynws/flv:sd/live",
            "http://223.110.242.130:6610/gitv/live1/G_NINGXIA/G_NINGXIA/",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=9",
            "http://118.123.60.6:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_cid=6625&Fsv_TBt=3072000&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_chan_hls_se_idx=12",
            "http://223.110.242.130:6610/gitv/live1/G_SHANXI/G_SHANXI/",
            "http://223.110.242.130:6610/gitv/live1/G_XIZANG/G_XIZANG/",
            "http://118.123.60.8:8114/syhjcms_hls/?FvSeid=&Fsv_filetype=0&Fsv_ctype=LIVES&Fsv_TBt=614400&Fsv_ShiftEnable=1&Fsv_ShiftTsp=120&Fsv_SV_PARAM1=0&Fsv_otype=0&Provider_id=syhjcms_hls&Pcontent_id=&Fsv_CMSID=syhjcms&Fsv_cid=6653&Fsv_chan_hls_se_idx=23",
            "http://223.110.245.167/ott.js.chinamobile.com/PLTV/3/224/3221226922/index.m3u8",
            "http://223.110.245.167/ott.js.chinamobile.com/PLTV/3/224/3221226923/index.m3u8",
            "http://183.207.249.35/PLTV/3/224/3221226975/index.m3u8",
            "http://live.italkdd.com/cds160/hls/channel001/channel001_2000.m3u8",
            "http://zhibo.hkstv.tv/livestream/zb2yhapo/playlist.m3u8",
            "http://222.207.48.30/hls/startv.m3u8"
    };

    public TvItemProgramAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        return mDataList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mDataList[position]);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TvLiveActivity.class);
                intent.putExtra("url", mUrlList[position]);
                intent.putExtra("title", mDataList[position]);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.tv_item_program_title);
        }
    }
}