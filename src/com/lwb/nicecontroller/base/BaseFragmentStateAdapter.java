package com.lwb.nicecontroller.base;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * FragmentStateAdapter基类
 * 
 * @author lwb
 * 
 */
public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

private List<Fragment> list = new LinkedList<Fragment>();
	
	public void addArray(Collection<? extends Fragment> c){
		list.addAll(c);
	}
	
	public void add(Fragment t){
		list.add(t);
	}
	
	public void remove(Fragment t){
		list.remove(t);
	}
	
	public void remove(int location){
		list.remove(location);
	}
	
	public void removeAll(Collection<? extends Fragment> c){
		list.removeAll(c);
	}
	
	public BaseFragmentStateAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
