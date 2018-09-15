package cn.nobitastudio.component.service;

public class backup {
/*         *
                 * 根据挂号单号，取消挂号记录。并且将取消本次就诊的就诊卡取消就诊次数+1,达到5次便冻结
     * 加强版，但是输出有问题，暂时不用
     * @param registrationNo 待取消挂号单的流水号

    @Override
    public void cancelRegistration(final String registrationNo, final HttpServletResponse response, final String type) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                boolean isSuccess = false;
                do {
                    int lines = iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(2));
                    isSuccess = (lines > 0);
                    if (isSuccess) {
                        MessageIsSuccess messageIsSuccess = new MessageIsSuccess(isSuccess);
                        HttpSendJsonUtil.sendMessageIsSuccessJsonToClient(response, messageIsSuccess, type);
                    }
                    count++;
                    try {
                        //sleep 2s to decrease strees on server.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!isSuccess && count != 35);

                if (!isSuccess && count == 35) {
                    MessageIsSuccess messageIsSuccess = new MessageIsSuccess(true);
                    iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(2));
                    HttpSendJsonUtil.sendMessageIsSuccessJsonToClient(response, messageIsSuccess, type);
                }

            }
        }).start();
    }

    *
            * 根据挂号单号，确认挂号记录。
            * 加强版，但是输出有问题，暂时不用
     * @param registrationNo 待确认挂号单的流水号

    @Override
    public void confirmRegistration(final String registrationNo, final HttpServletResponse response, final String type) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                boolean isSuccess = false;
                do {
                    int lines = iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(1));
                    isSuccess = (lines > 0);
                    if (isSuccess) {
                        MessageIsSuccess messageIsSuccess = new MessageIsSuccess(isSuccess);
                        HttpSendJsonUtil.sendMessageIsSuccessJsonToClient(response, messageIsSuccess, type);
                    }
                    count++;
                    try {
                        //sleep 2s to decrease strees on server.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!isSuccess && count != 35);

                if (!isSuccess && count == 35) {
                    MessageIsSuccess messageIsSuccess = new MessageIsSuccess(true);
                    iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(1));
                    HttpSendJsonUtil.sendMessageIsSuccessJsonToClient(response, messageIsSuccess, type);
                }

            }
        }).start();

    }*/
}
