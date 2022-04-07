package Distancias;

public class DistanceFactory implements Factory{
    @Override
    public Distance getDistance(DistanceType distanceType) {
        if (distanceType == DistanceType.EUCLIDEAN){
            return new EuclideanDistance();
        }
        return null;
    }
}
