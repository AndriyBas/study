package com.oyster.core.controller.command;

import com.oyster.app.AppConst;
import com.oyster.dao.impl.DAOCRUDJdbc;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author bamboo
 * @since 4/22/14 12:28 AM
 */

public abstract class AbstractCommand implements Runnable {

    protected Context context;
    protected Runnable onPostExecute;



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object addParameter(String name, Object value) {
        return context.put(name, value);
    }

    public Object removeParametr(String name) {
        return context.remove(name);
    }

    public Runnable getOnPostExecute() {
        return onPostExecute;
    }

    public void setOnPostExecute(Runnable runnable) {
        onPostExecute = runnable;
    }


}
