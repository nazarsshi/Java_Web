class DateFormat {
    getDefaultDateFormat = (str: String) => {
        const date = new Date(str + "Z");

        return this.getTwoSymbolsNumber(date.getDate()) +
            "/" + this.getTwoSymbolsNumber(date.getMonth() + 1) +
            "/" + this.getTwoSymbolsNumber(date.getFullYear()) +
            " " + this.getTwoSymbolsNumber(date.getHours()) +
            ":" + this.getTwoSymbolsNumber(date.getMinutes()) +
            ":" + this.getTwoSymbolsNumber(date.getSeconds());
    }

    getTwoSymbolsNumber = (num: number): string => {
        if (num.toString().length === 1) {
            return "0" + num;
        }
        return num.toString();
    }
}

export default DateFormat;
