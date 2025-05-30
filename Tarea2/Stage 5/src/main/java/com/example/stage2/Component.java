package com.example.stage2;

/**
 * Clase base para todos los componentes del patrón publicador-suscriptor.
 * Provee nombre y nombre de tópico asociados al componente.
 */
public class Component {
   /**
    * Constructor protegido para evitar instanciación sin datos.
    */
   protected Component (){}  // to ban creation of publisher or subscriber without name.

   /**
    * Constructor con nombre y tópico.
    *
    * @param componentName Nombre del componente (Publisher o Subscriber)
    * @param topicName     Nombre del tópico al que está vinculado
    */
   public Component(String componentName, String topicName){
      name=componentName;
      this.topicName = topicName;
   }

   /**
    * Retorna el nombre del componente.
    *
    * @return Nombre del componente
    */
   public String getName(){
      return name;
   }

   /**
    * Retorna el nombre del tópico.
    *
    * @return Nombre del tópico asociado
    */
   public String getTopicName(){
      return topicName;
   }
   protected String name;
   protected String topicName;
}