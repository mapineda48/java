export function ensureThis<T>(val: T): T;
export function ensureThis(val: any): any {
  const keys = getKeysProto(val, 2);

  const res = Object.fromEntries(
    keys.map((key) => {
      return [key, (...args: any[]) => val[key](...args)];
    })
  );

  return res;
}

function getKeysProto(
  obj: any,
  max: number,
  keys: string[] = [],
  current = 1
): string[] {
  const prot = Object.getPrototypeOf(obj);

  const _keys = [...Object.getOwnPropertyNames(prot), ...keys];

  if (current < max) {
    return getKeysProto(prot, max, _keys, current + 1);
  }

  return _keys
    .filter((c, index) => {
      return _keys.indexOf(c) === index;
    })
    .filter((key) => key !== "constructor");
}

export default ensureThis;
