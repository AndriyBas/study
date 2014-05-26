package com.oyster.core.controller.command;


import com.oyster.core.controller.CommandExecutor;

import javax.swing.*;

/**
 * Абстпрактний клас, що реалізує базову команду,
 * містить контекст, та екземпляр інтерфейсу Runnable,
 * та методи його отримання
 *
 * @author bamboo
 */

public abstract class AsyncCommand<Progress, Result> {

    private Context context;

    public AsyncCommand() {
    }

    public final AsyncCommand<Progress, Result> execute(final Context context) {

        this.context = context;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Result result = doInBackGround(context);
                postResult(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });
            }
        };

        // TODO ГРАБЛІ
        // ці костилі написані тому, що на момент написання
        // невідомо було, як саме зробити так,  щоб команди виконувались по черзі,
        // і метод onPreExecute() не викликався до того, як інша команда завершиться,
        // точніше, як зробити так, щоб метод onPreExecute() виконувався безпосередньо перед тим,
        // як виконається метод doInBackground()
        // УВАГА, ЗРОБЛЕНО ІЗ РОЗМІРКОУВАНЬ, ЩО ВСІ КОМАНДИ ВИКОНУЮТЬСЯ ПО ЧЕРЗІ У ОДНОМУ
        // BACKGROUND ПОТОЦІ, У ВИПАДКУ БАГАТЬОХ ПОТОКІВ ЩЕ ТРЕБА МІЗКУВАТИ
        CommandExecutor
                .getInstance()
                .getThreadExecutorService()
                .submit(new Runnable() {
                    @Override
                    public void run() {
                        postResult(new Runnable() {
                            @Override
                            public void run() {
                                onPreExecute();
                            }
                        });
                    }
                });

        CommandExecutor
                .getInstance()
                .getThreadExecutorService()
                .submit(runnable);

        return this;
    }

    protected final void publishProgress(final Progress... progress) {
        postResult(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }

    protected abstract Result doInBackGround(Context context);

    protected void onProgressUpdate(Progress... progress) {
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    private Result postResult(Result result) {
        return result;
    }

    private void postResult(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }

}
