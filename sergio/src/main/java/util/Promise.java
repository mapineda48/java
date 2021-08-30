package util;

public class Promise<T> {
    private Res<T> res = (res) -> {
    };
    private Rej rej = (err) -> {
    };

    public Promise(Task<T> task) {
        task.run(this.res, this.rej);
    }

    public Promise<T> then(Res<T> res) {
        this.res = res;

        return this;
    }

    public Promise<T> catchRej(Rej rej) {
        this.rej = rej;

        return this;
    }
}
