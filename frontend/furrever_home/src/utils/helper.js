
export const saveLocalStorage=(key,value) =>{
    localStorage.setItem(key,value);
}

export const deleteLocalStorage=(key) =>{
    localStorage.removeItem(key);
}

export const readLocalStorage=(key)=>{
    return localStorage.getItem(key);
}