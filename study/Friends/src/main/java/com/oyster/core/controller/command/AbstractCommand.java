package com.oyster.core.controller.command;


/**
 * Абстпрактний клас, що реалізує базову команду,
 * містить контекст, та екземпляр інтерфейсу Runnable,
 * та методи його отримання
 *
 * @author bamboo
 */

public abstract class AbstractCommand implements Runnable {

    protected Context context;
    protected Runnable onPostExecute;

    /**
     * повертає контекст команди
     *
     * @return контекст команди
     */
    public Context getContext() {
        return context;
    }

    /**
     * встановлює контекст команди
     *
     * @param context контекст команди
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * повертає екземпляр інтерфейсу Runnable, метод якого run()
     * буде виконаний у UI потоці
     *
     * @return екземпляр інтерфейсу Runnable
     */
    public Runnable getOnPostExecute() {
        return onPostExecute;
    }

    /**
     * встановлює екземпляр інтерфейсу Runnable, метод якого run()
     * буде виконаний у UI потоці
     *
     * @param runnable екземпляр інтерфейсу Runnable
     */
    public void setOnPostExecute(Runnable runnable) {
        onPostExecute = runnable;
    }


}
