export class BlobJSON extends Blob {
  constructor(obj: any) {
    super([JSON.stringify(obj)], { type: "application/json" });
  }
}

export default BlobJSON;
