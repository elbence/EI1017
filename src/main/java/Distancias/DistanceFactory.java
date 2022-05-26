package Distancias;

public class DistanceFactory implements Factory{
    @Override
    public Distance getDistance(DistanceType distanceType) {
        Distance distance = new EuclideanDistance();
        if (distanceType == DistanceType.MANHATTAN){
            distance = new ManhattanDistance();
        }
        return distance;
    }
}
