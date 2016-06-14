(function(){
    $( document ).ready(function() {
        $(".modificar").click(function(event){
            var formulario = $(this).parents('form:first');
            atualizaTarefa(formulario);
        });
        $(".tarefa").on('dblclick', function(){
            this.readOnly = !this.readOnly;
        });
    });

    function atualizaTarefa(formulario){
        var dados = {
            id: $("#tarefaId", formulario).text(),
            descricao: $("input[name='tarefaDescricao']", formulario).val(),
            prazo: $("input[name='tarefaPrazo']", formulario).val()
        };
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/tarefasJson",
            dataType: 'json',
            data: JSON.stringify(dados),
            success: function(objeto){
                console.log("funcionou" + objeto);
            },
            error: function(msg){
                console.log("erro" + msg);
            }
        });
    }
})();