package cn.crabime;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 * @author Crabime
 */
public class AppQuark {
    public static void access(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("����Ҫ��������������һ�������ߣ�һ��������");
        }
        String msgText = args[0];
        String destinationName = args[1];

        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        Thread.sleep(1000);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        Thread.sleep(1000);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        Thread.sleep(1000);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSConsumer(destinationName), false);
        thread(new JMSProducer(msgText, destinationName), false);
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public static class JMSProducer implements Runnable {
        private String msgText;
        private String destinationName;

        public JMSProducer(String msgText, String destinationName) {
            this.msgText = msgText;
            this.destinationName = destinationName;
        }

        //JMS send message
        @Override
        public void run() {
            Connection connection = null;
            Session session = null;
            try {
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
                connection = factory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(destinationName);

                //Producer should persist its message inside
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                String message = msgText + Thread.currentThread().getName() + ":" + this.hashCode();
                //construct a TextMessage instance, which is the medium of JMSProduer
                TextMessage textMessage = session.createTextMessage(message);
                System.out.println("Send message:" + message);

                producer.send(textMessage);

            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                try {
                    session.close();
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class JMSConsumer implements Runnable, javax.jms.ExceptionListener {
        private String destinationName;

        public JMSConsumer(String destinationName) {
            this.destinationName = destinationName;
        }

        //JMS receive message
        @Override
        public void run() {
            Connection connection = null;
            Session session = null;
            try {
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
                connection = factory.createConnection();
                connection.start();
                connection.setExceptionListener(this);
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(destinationName);

                MessageConsumer consumer = session.createConsumer(destination);
                Message message = consumer.receive(1000);

                if (message instanceof TextMessage) {
                    String text = ((TextMessage) message).getText();
                    System.out.println("Recieve message:" + text);
                } else {
                    System.out.println("Recieve message:" + message);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                try {
                    session.close();
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onException(JMSException arg0) {
            System.err.println("Message has some error, pipeline has been blocked");
        }

    }
}
