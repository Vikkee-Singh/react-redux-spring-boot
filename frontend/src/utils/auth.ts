const TOKEN_KEY: string = "jwtToken";
const USER_INFO: string = "userInfo";

const parse: any = JSON.parse;
const stringify = JSON.stringify;
const SALT: string = "asasJHGJHFG^%$^&76576";

const auth = {
    /**
     * Encrypt Data to be save in localstorage
     * @param  {Object} o [description]
     * @param  {String} salt [description]
     */
    encript(o: any, salt: string): string {
        o = JSON.stringify(o).split("");
        for (var i = 0, l = o.length; i < l; i++)
            if (o[i] === "{") o[i] = "}";
            else if (o[i] === "}") o[i] = "{";
        return encodeURI(salt + o.join(""));
    },
    /**
     * Decrypt Data to be save in localstorage
     * @param  {Object} o [description]
     * @param  {String} salt [description]
     */
    decript(o: any, salt: string): string {
        o = decodeURI(o);
        if (salt && o.indexOf(salt) !== 0) return "";
        // throw new Error("object cannot be decripted");
        o = o.substring(salt.length).split("");
        for (var i = 0, l = o.length; i < l; i++)
            if (o[i] === "{") o[i] = "}";
            else if (o[i] === "}") o[i] = "{";
        return JSON.parse(o.join(""));
    },
    /**
     * Remove an item from the used storage
     * @param  {String} key [description]
     */
    clear(key: string) {
        if (localStorage && localStorage.getItem(key)) {
            return localStorage.removeItem(key);
        }
        return null;
    },

    /**
     * Clear all app storage
     */
    clearAppStorage() {
        if (localStorage) {
            localStorage.clear();
        }
    },

    clearToken: (tokenKey = TOKEN_KEY) => auth.clear(tokenKey),

    clearUserInfo: (userInfo = USER_INFO) => auth.clear(userInfo),

    /**
     * Returns data from storage
     * @param  {String} key Item to get from the storage
     * @return {String|Object}     Data from the storage
     */
    get(key: string): any {
        if (localStorage && localStorage.getItem(key)) {
            return parse(localStorage.getItem(key)) || null;
        }
        return null;
    },

    getToken: (tokenKey = TOKEN_KEY) => auth.get(tokenKey),

    getUserInfo: (userInfo = USER_INFO) => auth.decript(auth.get(userInfo), SALT),

    /**
     * Set data in storage
     * @param {String|Object}  value    The data to store
     * @param {String}  key
     */
    set: (value: any, key: string) => {
        if (!value) {
            return null;
        }
        if (localStorage) {
            return localStorage.setItem(key, stringify(value));
        }
        return null;
    },

    setToken: (value: any = "", tokenKey: string = TOKEN_KEY) => auth.set(value, tokenKey),

    setUserInfo: (value: any = "", userInfo: string = USER_INFO) => {
        let enValue = auth.encript(value, SALT);
        return auth.set(enValue, userInfo);
    },
    isLogedIn: (): boolean => !(auth.getToken() == null)
};

export { auth };
