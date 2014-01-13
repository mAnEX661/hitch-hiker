package autostoppista.android.adapters;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ObservableCollection<T> extends BaseAdapter implements List<T> {	
	private ArrayList<T> lis;
	protected Context context;
	public ObservableCollection(Context context, int textViewResourceId) {
        lis = new ArrayList<T>();
        this.context = context;
    }
	public ArrayList<T> getList() {
		return lis;
	}
	public void setList(ArrayList<T> l) {
		lis = l;
	}
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
    @Override
    public void notifyDataSetChanged() {
    	super.notifyDataSetChanged();
    }
    @Override
    public void notifyDataSetInvalidated() {
    	super.notifyDataSetInvalidated();
    }
    @Override
    public int getCount() {
       return lis.size();
    }
    @Override
    public T getItem(int position) {
       return lis.get(position);
    }
    @Override
    public long getItemId(int position) {
       return position;
    }
	@Override
	public boolean add(T object) {
		boolean a = lis.add(object);
		super.notifyDataSetChanged();
		return a;
	}
	@Override
	public void add(int location, T object) {
		lis.add(location, object);
		super.notifyDataSetChanged();
	}
	@Override
	public boolean addAll(Collection<? extends T> collection) {
		boolean a = lis.addAll(collection);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public boolean addAll(int index, Collection<? extends T> collection) {
		boolean a = lis.addAll(index,collection);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public void clear() {
		lis.clear();
		notifyDataSetChanged();
	}
	@Override
	public boolean contains(Object object) {
		return lis.contains(object);
	}
	@Override
	public boolean containsAll(Collection<?> collection) {
		return lis.containsAll(collection);
	}
	@Override
	public T get(int location) {
		return lis.get(location);
	}
	@Override
	public int indexOf(Object object) {
		return lis.indexOf(object);
	}
	@Override
	public Iterator<T> iterator() {
		return lis.iterator();
	}
	@Override
	public int lastIndexOf(Object object) {
		return lis.lastIndexOf(object);
	}
	@Override
	public ListIterator<T> listIterator() {
		return lis.listIterator();
	}
	@Override
	public ListIterator<T> listIterator(int location) {
		return lis.listIterator(location);
	}
	@Override
	public T remove(int location) {
		T a =lis.remove(location);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public boolean remove(Object object) {
		boolean a =lis.remove(object);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean a =lis.removeAll(collection);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean a =lis.retainAll(arg0);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public T set(int location, T object) {
		T a =lis.set(location,object);
		notifyDataSetChanged();
		return a;
	}
	@Override
	public int size() {
		return lis.size();
	}
	@Override
	public List<T> subList(int start, int end) {
		return lis.subList(start, end);
	}
	@Override
	public Object[] toArray() {
		return lis.toArray();
	}
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] array) {
		T[] a =lis.toArray(array);
		return a;
	}
}
