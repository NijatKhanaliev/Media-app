
function likePost(userId){
    fetch(`/follow?userId=${userId}`,{
        method : "POST",
        headers:{
            "Authorization":"Bearer "+localStorage.getItem("jwtToken")
        }
    })
        .then(res=>{
            if(res.ok){
                window.location.href="/"
            }else{
                console.log("Unauthorized")
            }
        })
        .catch(err=>console.log("Error",err))
}