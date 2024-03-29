package mongodb.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Obtener la base de datos
            MongoDatabase database = mongoClient.getDatabase("admin");

            // Obtener la colección
            MongoCollection<Document> collection = database.getCollection("Pruebas");

            // Insertar un documento de ejemplo
            insertarDocumento(collection, "David", 28, "Albacete");

            // Consultar e imprimir todos los documentos en la colección
            System.out.println("Documentos antes de la modificación:");
            printAllDocuments(collection);

            // Modificar un documento
            modificarDocumento(collection, "Ejemplo", "edad", 35);

            System.out.println("Documentos después de la modificación:");
            printAllDocuments(collection);

            // Borrar un documento
            borrarDocumento(collection, "Ejemplo");

            System.out.println("Documentos después de la eliminación:");
            printAllDocuments(collection);
        }
    }

    private static void insertarDocumento(MongoCollection<Document> collection, String nombre, int edad, String ciudad) {
        Document document = new Document("nombre", nombre)
                .append("edad", edad)
                .append("ciudad", ciudad);
        collection.insertOne(document);
    }

    private static void modificarDocumento(MongoCollection<Document> collection, String nombre, String campo, Object valor) {
        collection.updateOne(new Document("nombre", nombre),
                new Document("$set", new Document(campo, valor)));
    }

    private static void borrarDocumento(MongoCollection<Document> collection, String nombre) {
        collection.deleteOne(new Document("nombre", nombre));
    }

    private static void printAllDocuments(MongoCollection<Document> collection) {
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }
}

