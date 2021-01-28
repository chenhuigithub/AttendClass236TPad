package com.example.teaching236pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 列表基础适配器
 * 
 * @author zhaochenhui_2017.08.25
 * 
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
	protected Context context;
	private List<T> dataList;

	public BaseListAdapter(Context context, List<T> dataList) {
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		if (dataList != null) {
			return this.dataList.size();
		} else {
			return 0;
		}
	}

	@Override
	public T getItem(int position) {
		if (dataList != null) {
			return this.dataList.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View resultView = convertView;
		if (resultView == null) {
			resultView = LayoutInflater.from(this.context).inflate(
					this.getLayoutResID(), null);
		}
		T dataObj = getItem(position);
		if ((resultView != null) && (dataObj != null)) {
			doAssignValueForView(position, resultView, dataObj);
		}

		return resultView;
	}

	/**
	 * 子类实现该方法返回layout资源ID
	 * 
	 * @return
	 */
	protected abstract int getLayoutResID();

	/**
	 * 子类进行赋值
	 * @param position 位置
     * @param resultView 布局
	 * @param dataObj 数据
	 */
	protected abstract void doAssignValueForView(int position, View resultView,
			T dataObj);

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 位置变换
	 */
	public static <T> List<T> indexExChange(List<T> list, int index1, int index2) {
		T t = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, t);
		return list;
	}

	/**
	 * 移除某项
	 */
	public static <T> List<T> removeItem(List<T> list, int index) {
		list.remove(index);
		return list;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
