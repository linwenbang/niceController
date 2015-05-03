package com.lwb.nicecontroller.base;



import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ListAdapter基类
 * 
 * @author lwb
 * 
 */

public class BaseListAdapter<T>extends BaseAdapter {
	
	private List<T> list = new LinkedList<T>();
	
	public void addArray(Collection<? extends T> c){
		list.addAll(c);
	}
	
	public void add(T t){
		list.add(t);
	}
	
	public void add(int location ,Collection<? extends T> c ) {
		list.addAll(location, c);
	}
	
	public void remove(T t){
		list.remove(t);
	}
	
	public void remove(int location){
		list.remove(location);
	}
	
	public void removeAll(Collection<? extends T> c){
		list.removeAll(c);
	}
	
	public void clear(){
		list.clear();
	}
	
	public List<T> getList(){
		return list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param o   viewholder
	 * @param t
	 * @param position
	 */
	protected void initView(Object o , T t , int position){
		
	}

}
