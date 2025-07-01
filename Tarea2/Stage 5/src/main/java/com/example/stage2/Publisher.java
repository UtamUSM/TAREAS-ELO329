package com.example.stage2;

/**
 * Representa un publicador que puede enviar mensajes a un tópico.
 */
public class Publisher extends Component {
    /**
     * Constructor protegido para prevenir uso por defecto.
     */
    protected Publisher() {} // to ban calls to this constructor
    /**
     * Crea un nuevo publicador vinculado a un tópico existente o nuevo.
     *
     * @param name       Nombre del publicador
     * @param broker     Instancia de Broker para crear/obtener el tópico
     * @param topicName  Nombre del tópico
     */
    public Publisher(String name, Broker broker, String topicName) {
        super(name, topicName);
        topic = broker.createTopic(topicName);
    }
    /**
     * Publica un nuevo mensaje en el tópico asociado.
     *
     * @param message Mensaje a publicar
     */
    protected void publishNewEvent(String message) {
        topic.notify(message);
    }
    private Topic topic;
}