import React, { Suspense } from "react";
import Loading from "../Loading";

const LazyShop = React.lazy(() => import("."));

export default function ShopSuspense() {
  return (
    <Suspense fallback={<Loading />}>
      <LazyShop />
    </Suspense>
  );
}
