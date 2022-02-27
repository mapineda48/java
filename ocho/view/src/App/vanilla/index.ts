export function setDataForm(el: HTMLElement, data: any) {
  const inputs = getElements(el, ["input", "select"]);

  Object.entries(data).forEach(([key, value]) => {
    inputs.forEach((input) => {
      if (!(input.name === key)) return;

      input.value = "" + value;
    });
  });
}

export function getDataForm<T = any>(el: HTMLElement): T {
  const inputs = getElements(el, ["input", "select"]);

  const res: any = Object.fromEntries(
    inputs.map(({ name, value, files, type }: any) => {
      if (type === "number") {
        return [name, parseFloat(value)];
      }

      if (type === "file") {
        return [name, files];
      }

      if (value === "true") {
        return [name, true];
      }

      if (value === "false") {
        return [name, false];
      }

      return [name, value];
    })
  );

  return res;
}

export function setLoading(el: HTMLElement) {
  const els = getElements(el, ["input", "button", "select"]);

  els.forEach((el) => (el.disabled = true));

  return () => {
    els.forEach((input) => (input.disabled = false));
  };
}

export function getElements<K extends keyof HTMLElementTagNameMap>(
  el: HTMLElement,
  tag: K[]
): HTMLElementTagNameMap[K][];
export function getElements<K extends keyof HTMLElementTagNameMap>(
  el: HTMLElement,
  tag: K
): HTMLElementTagNameMap[K][];
export function getElements(el: any, tag: any): any {
  if (!Array.isArray(tag)) {
    const els = Array.from(el.getElementsByTagName(tag));

    return els;
  }

  return tag
    .map((tag) => {
      const els = Array.from(el.getElementsByTagName(tag));

      return els;
    })
    .reduce((prev, current) => [...prev, ...current], []);
}
