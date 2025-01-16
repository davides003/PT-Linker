package data;

public class Chat {
    private int idMittente,idChat;
    private int idDestinatario;
    private String chat;

    // Costruttore con parametri
    public Chat(int idMittente, int idDestinatario, String chat, int idChat) {
        this.idChat = idChat;
        setIdMittente(idMittente);
        setIdDestinatario(idDestinatario);
        setChat(chat);
    }

    // Getters e Setters con controlli
    public int getIdMittente() {
        return idMittente;
    }

    public void setIdMittente(int idMittente) {
        if (idMittente <= 0) {
            throw new IllegalArgumentException("L'ID mittente deve essere un numero positivo.");
        }
        this.idMittente = idMittente;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int idDestinatario) {
        if (idDestinatario <= 0) {
            throw new IllegalArgumentException("L'ID destinatario deve essere un numero positivo.");
        }
        this.idDestinatario = idDestinatario;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        if (chat == null || chat.trim().isEmpty()) {
            throw new IllegalArgumentException("La chat non può essere vuota.");
        }
        this.chat = chat;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }
}

