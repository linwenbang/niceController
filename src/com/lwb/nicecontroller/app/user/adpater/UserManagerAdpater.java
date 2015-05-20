package com.lwb.nicecontroller.app.user.adpater;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.bean.UserManagerBean;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

/**
 * @author lwb 创建日期:2015-4-11 上午9:42:21
 */
public class UserManagerAdpater extends BaseAdapter {

	private List<UserManagerBean> mList;
	private LayoutInflater mInflater;
	private ViewHolder viewHolder;
	private Context mContext;

	public UserManagerAdpater(Context context, List<UserManagerBean> list) {
		this.mList = list;
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList != null && mList.size() > 0) {
			return mList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_manager_layout_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.txt_id = (TextView) convertView
					.findViewById(R.id.txt_id);
			viewHolder.txt_alisa = (TextView) convertView
					.findViewById(R.id.txt_alisa);
			viewHolder.txt_isAdmin = (TextView) convertView
					.findViewById(R.id.txt_isAdmin);
			viewHolder.btn_del = (Button) convertView
					.findViewById(R.id.btn_del);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String id = mList.get(position).getUserid().toString();
		if (!TextUtils.isEmpty(id)) {
			viewHolder.txt_id.setText(id);
		} else {
			viewHolder.txt_id.setText("id未知");
		}

		String alisa = mList.get(position).getAlias().toString();
		if (!TextUtils.isEmpty(alisa)) {
			viewHolder.txt_alisa.setText(alisa);
		} else {
			viewHolder.txt_id.setText("alisa未知");
		}

		String isAdmin = mList.get(position).getIsAdmin().toString();
		if (!TextUtils.isEmpty(isAdmin)) {
			viewHolder.txt_isAdmin.setText(isAdmin);
		}

		viewHolder.btn_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				delUser(position);

			}

		});
		return convertView;
	}

	/**
	 * 删除注册用户 DELETE: /api/v2.0/regist/{userid}/{deluserid}
	 */
	private void delUser(final int pos) {
		// TODO Auto-generated method stub
		String url = UrlContants.getDEL_USERS_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));
		reqParam.put("{deluserid}", mList.get(pos).getUserid().toString());

		url = UrlContants.creatUrl(url, reqParam);

		HttpUtils.delete(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String json = null;
				try {
					json = new String(arg2, "GB2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("返回结果" + json);
				int code = FastjsonUtils.getCode(json);
				switch (code) {
				case 444:
				case 202:
				case 200:
					DialogBtn.showDialog(mContext, "刪除成功");
					mList.remove(pos);
					notifyDataSetChanged();
					break;

				default:
					DialogBtn.showDialog(mContext,
							FastjsonUtils.getSummary(json));
					break;
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	class ViewHolder {
		Button btn_del;
		TextView txt_id;
		TextView txt_alisa;
		TextView txt_isAdmin;
	}
}
