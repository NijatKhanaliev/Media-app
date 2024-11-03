
    function createPost(name,desc,file){
        const formData = new FormData();

        formData.append("name",name)
        formData.append("description",desc)
        formData.append("file",file)

        fetch("/create",{
            method:"POST",
            headers:{
                "Authorization":"Bearer " + localStorage.getItem("jwtToken")
            }
        })
            .then(resp=>{
                if (resp.ok){
                    window.location.href = "/"
                }else{
                    console.log("Unauthorized request")
                }
            })
            .catch(err=>console.log("Error",err))

    }