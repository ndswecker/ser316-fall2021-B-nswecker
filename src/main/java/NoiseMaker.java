/*
  File:     NoiseMaker.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

/**
 * Class: NoiseMaker.
 * Description: An object that has a string recording and a location.
 */
public class NoiseMaker {
    public double price;
    String label;
    String recording;

    Location spot;

    public NoiseMaker() {
        this("Default Noise", "I wuv you", Location.CENTERBODY);
    }

    /**
     * Main constructor
     * @param label is a string for identifying.
     * @param  recording is a string that would be vocalized.
     * @param location is a location in the bear specified by an enum.
     */
    public NoiseMaker(String label, String recording, Location location) {
        this.label = label;
        this.recording = recording;
        this.spot = location;
        switch (location) {
            // you can assume that the price given here for the noisemakers is correct
            case CENTERBODY:
                this.price = 10;
                break;
            default:
                this.price = 5;
                break;
        }
    }

    public enum Location {
        RIGHT_HAND, LEFT_HAND, RIGHT_FOOT, LEFT_FOOT, CENTERBODY
    }
}
