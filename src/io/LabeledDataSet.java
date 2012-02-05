package io;

import java.util.*;

import classify.LabeledDatum;

public class LabeledDataSet<T extends LabeledDatum<F,L>,F,L> extends DataSet<T,F> {
	
	Map<L,Integer> labelSet;
	final boolean builtWithLabelSet;
	int labelCount;
	
	public LabeledDataSet()
	{
		builtWithLabelSet = false;
		labelCount = 0;
		labelSet = new HashMap<L,Integer>();
	}
	
	public LabeledDataSet(int capacity)
	{
		super(capacity);
		labelCount = 0;
		builtWithLabelSet = false;
		labelSet = new HashMap<L,Integer>();
	}
	
	public LabeledDataSet(int capacity, Set<String> Vocab)
	{
		super(capacity,Vocab);
		labelCount = 0;
		builtWithLabelSet = false;
		labelSet = new HashMap<L,Integer>();
	}
	
	public LabeledDataSet(int capacity, Set<String> Vocab, Collection<L> labelSet)
	{
		super(capacity,Vocab);
		
		labelCount = 0;
		this.labelSet = new HashMap<L,Integer>();
		builtWithLabelSet = true;
		for(L label : labelSet)
			if( !this.labelSet.containsKey(label) )
				this.labelSet.put(label, labelCount++);
	}
	
	@Override
	public boolean add(T Datum)
	{
		L label = Datum.getLabel();
		
		if( !builtWithLabelSet && !labelSet.containsKey(label) )
			labelSet.put(label, labelCount++);
		else if( builtWithLabelSet && !labelSet.containsKey(label) )
		{
			System.err.println("Unknown label. So this data item is not added to the set");
			return false;
		}
		
		Data.add(Datum);
		return true;
	}
	
	public L getLabelMapping(Integer i)
	{
		for(L label : labelSet.keySet() )
			if(labelSet.get(label) == i)
				return label;
		return null;
	}
	
	public int getLabelIndex(L l)
	{
		return labelSet.get(l);
	}
	
	public int getCatSize()
	{
		return labelSet.size();
	}
	
	public Set<L> getLabelSet()
	{
		return labelSet.keySet();
	}

}
