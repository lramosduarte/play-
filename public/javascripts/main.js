(function(){
    $( document ).ready(function() {
        $(".tarefa").on('dblclick', function(){
            if(!this.readOnly){
                atualizaTarefa(this);
            }
            this.readOnly = !this.readOnly;
        });
    });

    function atualizaTarefa(tarefa){
        //não vou mais fazer por ajax
        console.log("atualizando tarefa");
    }
})();