import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContaCorrenteTest {

    // para cobrir pequenos erros de precisão do tipo float
    private float FLOAT_DELTA = 0.00001f;

    private ContaCorrente contaDoJoao;
    private Correntista joao;
    private ContaCorrente contaDaMaria;
    private Correntista maria;
    private float saldoInicial;

    @Before
    public void setUp() {
        joao = new Correntista("joao",11111 );
        contaDoJoao = new ContaCorrente(1, joao);
        saldoInicial = contaDoJoao.getSaldoEmReais();

        maria = new Correntista("Maria", 22222);
        contaDaMaria = new ContaCorrente(2, maria);
    }

    @Test
    public void testarSaldoInicialDaConta() {
        assertEquals("Toda conta, ao ser criada, deve começar com saldo de R$10,00.",
                10.0,
                saldoInicial,
                FLOAT_DELTA);
    }

    @Test
    public void testarRecebimentoDepositoParaValoresValidos() {
        contaDoJoao.receberDepositoEmDinheiro(50);
        assertEquals("O saldo da conta deve ser atualizado após receber um depósito",
                saldoInicial + 50,
                contaDoJoao.getSaldoEmReais(),
                FLOAT_DELTA);
    }

    @Test
    public void testarRecebimentoDepositoParaValoresNegativos() {
        contaDoJoao.receberDepositoEmDinheiro(-200);
        assertEquals("Depósitos de valores negativos devem ser solenemente ignorados",
                saldoInicial,
                contaDoJoao.getSaldoEmReais(),
                FLOAT_DELTA);
    }

    @Test
    public void testarRecebimentoDepositoParaValorZero() {
        String extratoAntes = contaDoJoao.getExtrato();

        contaDoJoao.receberDepositoEmDinheiro(0);
        assertEquals("Depósitos de valor zero devem ser ignorados",
                saldoInicial,
                contaDoJoao.getSaldoEmReais(),
                FLOAT_DELTA);

        String extratoDepois = contaDoJoao.getExtrato();

        assertEquals("Depósitos ignorados não devem sequer constar do extrato",
                extratoAntes, extratoDepois);

    }

    @Test
    public void testarSaqueComFundos() {
        contaDoJoao.sacar(2);
        assertEquals("O valor sacado deve ser descontado do saldo da conta",
                saldoInicial - 2,
                contaDoJoao.getSaldoEmReais()
        );
    }

    @Test
    public void testarSaqueSemFundos() {
        contaDoJoao.sacar(100000);
        assertEquals("Saques de valores maiores que o saldo não devem ser permitidos",
                saldoInicial,
                contaDoJoao.getSaldoEmReais()
        );
    }

    @Test
    public void testarTransferencia() {

        contaDoJoao.efetuarTransferecia(contaDaMaria, 3);

        assertEquals("O valor da conta que recebe a transferencia deve ter o saldo aumentado em 3, pois recebeu esse valor da outra conta.",
                 saldoInicial + 3,
                contaDaMaria.getSaldoEmReais(),
                FLOAT_DELTA
        );

        assertEquals("O valor da conta que efetuou a transferencia deve ter o saldo reduzido em 3, pois trasferiu esse valor para outra conta.",
                saldoInicial - 3,
                contaDoJoao.getSaldoEmReais(),
                FLOAT_DELTA
        );
    }

    @Test
    public void testarTransferenciaSemFundos() {

        contaDoJoao.efetuarTransferecia(contaDaMaria, 100000);

        assertEquals("A conta deve ter o mesmo valor que o saldo inicial pois nao tinha saldo para transferencia",
                saldoInicial,
                contaDaMaria.getSaldoEmReais(),
                FLOAT_DELTA
        );

        assertEquals("A conta deve ter o mesmo valor que o saldo inicial pois nao tinha saldo para transferencia",
                saldoInicial,
                contaDoJoao.getSaldoEmReais(),
                FLOAT_DELTA
        );
    }

    @Test
    public void testarQuantidadeDeTransacoesDeTodasAsContas() {
        Correntista pedro = new Correntista("pedro",33333 );
        ContaCorrente contaDoPedro = new ContaCorrente(3, pedro);
        Correntista paulo = new Correntista("paulo",33333 );
        ContaCorrente contaDoPaulo = new ContaCorrente(3, pedro);



        contaDoJoao.receberDepositoEmDinheiro(300);
        contaDoPaulo.receberDepositoEmDinheiro(50);
        contaDoPedro.receberDepositoEmDinheiro(502);
        contaDoPaulo.receberDepositoEmDinheiro(125);

        assertEquals("Checar numero de tranferencia em todas as contas correntes" ,
                4,
                ContaCorrente.getQuantidadeDeTransacoesDeTodasAsContas(),
                FLOAT_DELTA
         );

    }
    @Test
    public void testarGetCpfDoCorrentista(){
        Correntista pedro = new Correntista("pedro",33333 );
        ContaCorrente contaDoPedro = new ContaCorrente(3, pedro);

        assertEquals("Checar se o getCpfDoCorrentistaFunciona ",
                33333,
                Correntista.GetCpfDoCorrentista(),
                FLOAT_DELTA
        );

    }
}