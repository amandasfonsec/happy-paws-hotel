package util;

public class ValidarCpf {

    public static boolean validarCPF(String cpf) {
        boolean valida = false;
        String cpfLimpo = limpar(cpf);
        int mult = 10;
        int soma = 0;
        int dig1 = 0;
        int dig2 = 0;

        for (int i = 0; i < cpfLimpo.length() - 2; i++) {
            soma += mult * Integer.parseInt(cpfLimpo.charAt(i) + "");
            mult--;
        }

        if (soma % 11 < 2) {
            dig1 = 0;
        } else {
            dig1 = 11 - (soma % 11);
        }

        soma = 2 * dig1;
        mult = 11;
        for (int i = 0; i < cpfLimpo.length() - 2; i++) {
            soma += mult * Integer.parseInt(cpfLimpo.charAt(i) + "");
            mult--;
        }

        if (soma % 11 < 2) {
            dig2 = 0;
        } else {
            dig2 = 11 - (soma % 11);
        }

        if (dig1 == Integer.parseInt(cpfLimpo.charAt(9) + "")
                && dig2 == Integer.parseInt(cpfLimpo.charAt(10) + "")) {
            valida = true;
        }

        return valida;

    }

    public static String limpar(String doc) {
        String cnpjLimpo = "";
        for (int i = 0; i < doc.length(); i++) {
            if (doc.charAt(i) == '.' || doc.charAt(i) == '/' || doc.charAt(i) == '-') {
                continue;
            } else {
                cnpjLimpo += doc.charAt(i);
            }
        }
        return cnpjLimpo;
    }

}
