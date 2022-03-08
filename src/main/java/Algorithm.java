public interface Algorithm<T extends Table,R,A> {
    public void train (T data);
    public R estimate (A data);
}
