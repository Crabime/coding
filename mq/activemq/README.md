ActiveMQѧϰ����
================

����Spring��jms֧��ʱ��maven���нű����£�
<pre><code>mvn test -Dtest=SpringContextTest</pre></code>
�������Ա�������û��Ҫ�Ĳ�������

�ʼ��ĿΪһ���򵥵�java��Ŀ������Ϊ��ѧϰActiveMQ��web service�е�Ӧ�ã����ｫ��ͨmavenת��Ϊdynamic web service�ṹ��
*��ͨmaven��Ŀת��ΪWeb maven����Ŀ¼����*��
***
right click project --> properties --> project facets --> choose dynamic web service
***

*����ActiveMQ Spring֧��*
�����Ѹĳ�web service�󣬺ܶ�֮ǰд��main����������֮ǰ�������У�����ͨ��maven���е�Ԫ���ԡ�����Ҫ˵����ActiveMQ��Spring��֧��
�ֽ�����õ�beans.xml�ļ��ŵ�*src/test/resources*Ŀ¼�£�Ȼ��ִ��`mvn test -Dtest=SpringContextTest`������ĳһ������Ĳ����࣬����mvnһ�½�testĿ¼�����еĲ����඼�����ꡣ
### maven���롢��������ǲ���Ҫ���в����� ###
`clean compile install -X -DskipTests`