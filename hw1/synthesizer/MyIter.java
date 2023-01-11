package synthesizer;
/*
 * @File:   MyIter.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/11 下午3:16
 * @Version:0.0
 */

import java.util.Iterator;

public class MyIter<T> implements Iterator<T> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
