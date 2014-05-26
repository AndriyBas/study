package com.oyster.core.controller.command;

/**
 * Created by bamboo on 26.05.14.
 */
public class TestCommand extends AsyncCommand<Integer, Long> {

    private final String name;

    public TestCommand(String name) {
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        System.out.println(name + " : done onPreExecute()");
    }

    @Override
    protected Long doInBackGround(Context context) {
        Integer power = (Integer) context.get("power");
        Long res = 1L;

        for (int i = 0; i < power; i++) {
            res <<= 1;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i, power);
        }

        return res;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        System.out.println(name + " : current progress is : " + 1.0 * progress[0] / progress[1]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        System.out.println(name + " : done onPostExecute()");
        System.out.println(name + " : result = " + aLong);
    }
}
