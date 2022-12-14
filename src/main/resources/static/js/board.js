let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save: function(){
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            // 글쓰기 수행 요청
            type:"POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("글쓰기가 완료되었습니다.");
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); 

    },

    deleteById: function(){
        let id = $("#id").text();

        $.ajax({
            // 글 삭제 요청
            type:"DELETE",
            url: "/api/board/"+id,
            dataType: "json"
        }).done(function(resp){
            alert("삭제가 완료되었습니다.");
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    },

    update: function(){
        let id = $("#id").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            // 글 수정 수행 요청
            type:"PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("글수정이 완료되었습니다.");
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    }

};

index.init();