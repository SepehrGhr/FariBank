package ir.ac.kntu;

import java.util.Random;

enum Method {ACCOUNT, CONTACT}

public class TransferReceipt extends Receipt {
    private User transmitter;
    private User receiver;
    private String transferId;
    private Method method;

    public TransferReceipt(int amount, User transmitter, User receiver, Method method) {
        super(amount);
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.transferId = generateTransferID();
        this.method = method;
    }


    private String generateTransferID() {
        String transferID = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            transferID += String.valueOf(random.nextInt(10));
        }
        return transferID;
    }

    @Override
    public String toString() {
        if(method.equals(Method.CONTACT)){
            return super.toString() + Color.WHITE + "Transfer method : " + Color.BLUE + "to contact" +
                    '\n' + Color.WHITE + "Transmitter : " + Color.BLUE + transmitter.getName() + " " +
                    transmitter.getLastName() + '\n' + Color.WHITE + "Receiver : " + Color.BLUE +
                    transmitter.findContact(receiver.getPhoneNumber()).getName() + " " +
                    transmitter.findContact(receiver.getPhoneNumber()).getLastName() + '\n' +
                    Color.WHITE + "Transfer ID : " + Color.BLUE + transferId + '\n' + Color.CYAN +
                    "*".repeat(35) + Color.RESET;
        }
        return super.toString() + Color.WHITE + "Transfer method : " + Color.BLUE + "by AccountID" +
                '\n' + Color.WHITE + "Transmitter : " + Color.BLUE + transmitter.getName() + " " +
                transmitter.getLastName() + '\n' + Color.WHITE + "Receiver : " + Color.BLUE +
                receiver.getName() + " " + receiver.getLastName() + '\n' +
                Color.WHITE + "Transfer ID : " + Color.BLUE + transferId + '\n' + Color.CYAN +
                "*".repeat(35) + Color.RESET;
    }
}
