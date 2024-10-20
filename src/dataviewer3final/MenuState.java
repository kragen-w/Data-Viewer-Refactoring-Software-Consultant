package dataviewer3final;

public class MenuState implements GUIMode{

	@Override
	public GUIMode menu() {
		// TODO Auto-generated method stub
		return new MenuState();
	}

	@Override
	public GUIMode plot() {
		// TODO Auto-generated method stub
		return new PlotState();
	}
	

}
