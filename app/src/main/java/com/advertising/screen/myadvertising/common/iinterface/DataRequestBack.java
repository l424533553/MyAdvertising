package com.advertising.screen.myadvertising.common.iinterface;

import org.jetbrains.annotations.NotNull;

public interface DataRequestBack<T> {
    /**
     * 自动默认数据请求
     */
    int DATA_TYPE_AUTO = 0;
    int DATA_TYPE_NET = 1;
    int DATA_TYPE_DATABASE = 2;
    int DATA_TYPE_CACHE = 3;

    void onResponse(@NotNull T t, int dataType);

    void onFailure(@NotNull Throwable t, int dataType);
}