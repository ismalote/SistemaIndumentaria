package main.java.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
	List<IObserver> observers = new ArrayList<>();
	
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update();
		}
	}
}
