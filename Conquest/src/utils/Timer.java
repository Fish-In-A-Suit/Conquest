package utils;

/**
 * @author Aljoša
 * 
 * This class deals with various time-measuring methods, which are currently mainly used by the
 * gameLoop() method of the Main class
 *
 */
public class Timer {
	private double lastLoopTime;
	
    public void init() {
        lastLoopTime = getRealTime();
    }
    
    public float getElapsedTime() {
        double time = getRealTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }
	
	public double getRealTime() {
		return System.nanoTime() / 1_000_000_000.0;
	}
	
    public double getLastLoopTime() {
        return lastLoopTime;
    }

}
